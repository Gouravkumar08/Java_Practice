# Reactive Programming with Spring Boot — From Basics to Advanced

---

## 1. Core Concepts (Basics)

### What is Reactive Programming?

Reactive Programming is a programming paradigm built around **asynchronous data streams**. Instead of your code pulling data and blocking until it's ready, data is *pushed* to your code as it becomes available. You declare a pipeline of transformations, and the framework drives data through it when events occur.

### Why use it?

| Benefit | Explanation |
|---|---|
| **Non-blocking I/O** | Threads aren't held hostage waiting on DB/network calls — they're freed to do other work. |
| **Scalability** | A small, fixed thread pool (e.g., 4–8 threads) can handle thousands of concurrent connections, vs. one-thread-per-request in traditional servlet stacks. |
| **Resource efficiency** | Less memory/CPU overhead from idle blocked threads → lower infra cost under high load. |
| **Resilience** | Built-in operators for retries, timeouts, and fallback make failure handling first-class, not an afterthought. |
| **Backpressure support** | Consumers can tell producers to slow down, preventing a fast producer from overwhelming a slow consumer. |

### Where it fits

Reactive shines in **I/O-bound, high-concurrency** systems: API gateways, microservices calling other microservices, streaming data pipelines, real-time dashboards, chat/notification systems. It's *not* a silver bullet for CPU-bound work (heavy computation) — there, threads block on CPU, not I/O, so reactive offers little benefit and adds complexity.

### Key Terms (Reactive Streams Specification)

Reactive Streams is a spec (implemented by Project Reactor, RxJava, Akka Streams) defining four interfaces:

- **Publisher** — the source of data. Emits items to a Subscriber. (`Publisher<T>`)
- **Subscriber** — consumes items emitted by a Publisher. Has callbacks: `onSubscribe`, `onNext`, `onError`, `onComplete`.
- **Subscription** — the contract between Publisher and Subscriber. The Subscriber uses it to `request(n)` items or `cancel()`.
- **Processor** — both a Subscriber and a Publisher at once; transforms data as it passes through (rarely used directly in app code — operators like `map` do this for you).
- **Backpressure** — the mechanism by which a Subscriber controls the rate at which a Publisher sends data, via `request(n)`, so it's never overwhelmed.

```
Publisher  ----items---->  Subscriber
    ^                          |
    |------ request(n) --------|
       (this is backpressure)
```

### Reactive vs. Imperative — The Restaurant Analogy

**Imperative (blocking) = one waiter per table, waiter stands at the kitchen window until the food is ready.**
The waiter (thread) takes an order, walks to the kitchen, and *stands there blocking* until the dish is cooked — doing nothing else. If you have 200 tables, you need 200 waiters, even though most of the time they're just standing around waiting.

**Reactive (non-blocking) = a few waiters + a kitchen ticket system (conveyor belt).**
A waiter drops off an order ticket and immediately goes to serve another table. When a dish is ready, it's placed on a conveyor belt and *pushed* to whichever waiter is free to deliver it. A handful of waiters can serve hundreds of tables because no one is ever standing idle blocked on one task.

```java
// IMPERATIVE — thread blocks until result is available
User user = userRepository.findById(id);       // thread waits here
Order order = orderService.getOrders(user);     // thread waits here too
return order;

// REACTIVE — thread is released immediately, callback fires later
Mono<User> userMono = userRepository.findById(id);
return userMono.flatMap(user -> orderService.getOrders(user));
// thread is free to handle other requests while waiting
```

---

## 2. Reactive Libraries in Spring Boot

### Project Reactor: `Mono` and `Flux`

Spring's reactive stack is built on **Project Reactor**, which implements Reactive Streams with two core publisher types:

| Type | Emits | Use case |
|---|---|---|
| `Mono<T>` | **0 or 1** item, then completes (or errors) | Single DB record, a single API response, a void/completion signal |
| `Flux<T>` | **0 to N** items, then completes (or errors) | A list of records, a stream of events, paginated results, SSE streams |

```java
Mono<User> singleUser = userRepository.findById(1L);      // at most one user
Flux<User> allUsers   = userRepository.findAll();         // many users
Mono<Void> deleted    = userRepository.deleteById(1L);    // no value, just completion signal
```

Rule of thumb: **if you'd return `Optional<T>` or a single object imperatively → `Mono<T>`. If you'd return `List<T>` → `Flux<T>`.**

### WebFlux

`spring-boot-starter-webflux` is Spring's reactive web framework — an alternative to `spring-boot-starter-web` (Spring MVC). It runs on **Netty** by default (not Tomcat) and lets you write controllers that return `Mono`/`Flux` instead of plain objects, keeping the entire request lifecycle non-blocking end-to-end.

> ⚠️ Mixing WebFlux with blocking code (e.g., a blocking JDBC repository) **defeats the purpose** — see Pitfalls section.

### Project Setup

**`pom.xml` dependencies:**

```xml
<dependencies>
    <!-- Reactive web framework (Netty + WebFlux) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <!-- For reactive testing -->
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Optional: reactive SQL access -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    </dependency>
    <dependency>
        <groupId>io.asyncer</groupId>
        <artifactId>r2dbc-mysql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

**`application.yml`:**

```yaml
spring:
  r2dbc:
    url: r2dbc:mysql://localhost:3306/userdb
    username: root
    password: secret

server:
  port: 8080

# Netty thread pool tuning (optional, defaults are usually fine)
reactor:
  netty:
    ioWorkerCount: 4
```

No `@EnableWebFlux` annotation is needed — Spring Boot auto-configures it the moment it sees `spring-boot-starter-webflux` on the classpath (and *not* `spring-boot-starter-web`, since they conflict).

---

## 3. Practical Implementation — Reactive API Gateway

**Scenario:** A `/api/users` endpoint that calls a downstream user-service via `WebClient`, filters out inactive users, enriches each with a formatted display name, and handles errors gracefully.

### Step 1: WebClient Configuration

```java
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient userServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9090/internal/users") // downstream service
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
```

### Step 2: DTOs

```java
public record User(Long id, String firstName, String lastName, boolean active) {}

public record UserResponse(Long id, String displayName) {}
```

### Step 3: Service Layer with Operator Chain

```java
@Service
public class UserGatewayService {

    private final WebClient webClient;

    public UserGatewayService(WebClient userServiceWebClient) {
        this.webClient = userServiceWebClient;
    }

    public Flux<UserResponse> getActiveUsers() {
        return webClient.get()
                .uri("/all")
                .retrieve()
                // Map 4xx/5xx responses to a custom exception instead of generic WebClientException
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new DownstreamServiceException("User service failed: " + body))))
                .bodyToFlux(User.class)                       // deserialize JSON array -> Flux<User>
                .filter(User::active)                          // keep only active users
                .map(user -> new UserResponse(                 // transform User -> UserResponse
                        user.id(),
                        user.firstName() + " " + user.lastName()))
                .timeout(Duration.ofSeconds(3))                 // fail fast if downstream is slow
                .doOnNext(u -> log.info("Processed user: {}", u.id()))   // side-effect logging, doesn't alter the stream
                .doOnError(e -> log.error("Error fetching users", e))    // log before recovery
                .onErrorResume(DownstreamServiceException.class, e ->
                        Flux.empty())                            // graceful fallback: empty list instead of 500
                .onErrorResume(TimeoutException.class, e ->
                        Flux.error(new ResponseStatusException(
                                HttpStatus.GATEWAY_TIMEOUT, "User service timed out")));
    }

    // Example of flatMap: for each active user, fetch their order count from another service
    public Flux<UserResponse> getActiveUsersWithOrderCount(WebClient orderServiceClient) {
        return getActiveUsers()
                .flatMap(userResp -> orderServiceClient.get()
                        .uri("/orders/count/{id}", userResp.id())
                        .retrieve()
                        .bodyToMono(Integer.class)
                        .map(count -> new UserResponse(
                                userResp.id(),
                                userResp.displayName() + " (" + count + " orders)"))
                        .onErrorReturn(userResp) // if order-count call fails, fall back to original userResp
                );
    }
}
```

**Operator cheatsheet used above:**
- `map` — synchronous 1-to-1 transformation (`User` → `UserResponse`).
- `flatMap` — async 1-to-1-or-more transformation that itself returns a `Mono`/`Flux` (e.g., calling another reactive service per item). **Use `flatMap` whenever the transformation function returns a reactive type**; `map` cannot do this.
- `filter` — keep only items matching a predicate.
- `doOnNext` / `doOnError` — side effects (logging, metrics) that don't change the emitted values.
- `onErrorResume` — catch an error and **switch to a fallback Publisher**, recovering the stream.
- `onErrorReturn` — catch an error and substitute a single fixed value.

### Step 4: Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserGatewayService userGatewayService;

    public UserController(UserGatewayService userGatewayService) {
        this.userGatewayService = userGatewayService;
    }

    @GetMapping
    public Flux<UserResponse> getUsers() {
        return userGatewayService.getActiveUsers();
        // WebFlux subscribes to this Flux automatically and streams
        // the response back as JSON — you never call .subscribe() yourself in a controller.
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUser(@PathVariable Long id) {
        return userGatewayService.getActiveUsers()
                .filter(u -> u.id().equals(id))
                .next()                                        // take the first matching element as a Mono
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
```

### Step 5: Testing with `StepVerifier`

`StepVerifier` lets you assert on a reactive stream step-by-step without needing a running server.

```java
@ExtendWith(MockitoExtension.class)
class UserGatewayServiceTest {

    @Test
    void getActiveUsers_filtersInactiveAndTransforms() {
        // Arrange: mock WebClient call to return 2 users, one inactive
        WebClient mockClient = buildMockWebClient(List.of(
                new User(1L, "Gourav", "Sharma", true),
                new User(2L, "Inactive", "User", false)
        ));
        UserGatewayService service = new UserGatewayService(mockClient);

        // Act + Assert
        StepVerifier.create(service.getActiveUsers())
                .expectNextMatches(u -> u.displayName().equals("Gourav Sharma"))
                .verifyComplete();      // confirms exactly 1 item, then onComplete — inactive user was filtered out
    }

    @Test
    void getActiveUsers_onDownstreamError_returnsEmptyFlux() {
        WebClient failingClient = buildFailingWebClient();
        UserGatewayService service = new UserGatewayService(failingClient);

        StepVerifier.create(service.getActiveUsers())
                .verifyComplete();      // onErrorResume(Flux.empty()) means stream completes with zero items, no error
    }

    @Test
    void mono_example_userNotFound() {
        Mono<User> empty = Mono.empty();

        StepVerifier.create(empty)
                .verifyComplete();      // no onNext emitted, just completion
    }
}
```

Key `StepVerifier` methods: `expectNext(...)`, `expectNextMatches(predicate)`, `expectError(Class)`, `verifyComplete()`, `verifyError()`, `expectNextCount(n)`, and `withVirtualTime()` for testing delays/timeouts without real waiting.

---

## 4. Advanced Topics

### Backpressure Strategies

When a Publisher emits faster than a Subscriber can consume, you choose a strategy via `onBackpressureXxx()` operators:

```java
Flux<Integer> fastSource = Flux.range(1, 1_000_000);

fastSource.onBackpressureBuffer(1000)       // BUFFER: queue up to 1000 items, then error if exceeded
          .subscribe(slowConsumer);

fastSource.onBackpressureDrop(dropped ->    // DROP: discard new items if consumer is behind
                  log.warn("Dropped: {}", dropped))
          .subscribe(slowConsumer);

fastSource.onBackpressureLatest()           // LATEST: keep only the most recent item, discard older ones
          .subscribe(slowConsumer);
```

- **BUFFER** — use when occasional bursts are fine and you can afford temporary memory growth (e.g., batch log processing).
- **DROP** — use when missing some items is acceptable and most-recent isn't critical (e.g., high-frequency sensor pings where only trend matters).
- **LATEST** — use when only the newest state matters and stale data should be discarded (e.g., live stock price ticker — you don't care about every tick, just the latest).

### Combining Streams

```java
Mono<User> userMono = userService.getUser(1L);
Mono<List<Order>> ordersMono = orderService.getOrders(1L);

// zip: wait for BOTH to complete, combine results pairwise — like a Promise.all
Mono<UserProfile> profile = Mono.zip(userMono, ordersMono)
        .map(tuple -> new UserProfile(tuple.getT1(), tuple.getT2()));

// merge: interleave items from multiple sources AS THEY ARRIVE (no ordering guarantee)
Flux<Event> allEvents = Flux.merge(kafkaEventStream, dbChangeStream);

// concat: subscribe to sources ONE AT A TIME, in order — second starts only after first completes
Flux<User> ordered = Flux.concat(premiumUsersFlux, regularUsersFlux);
```

Use `zip` when you need *all* results together (e.g., dashboard combining 3 API calls). Use `merge` for independent streams where arrival order doesn't matter (e.g., combining two real-time event sources). Use `concat` when ordering/sequencing matters (e.g., process all premium users before regular ones).

### Schedulers & Threading

Reactor runs on event-loop threads by default. You control *where* work happens with `publishOn` / `subscribeOn` and these built-in schedulers:

| Scheduler | Use for |
|---|---|
| `Schedulers.parallel()` | CPU-intensive, non-blocking computation (fixed pool = number of CPU cores) |
| `Schedulers.boundedElastic()` | Blocking calls you can't avoid (legacy blocking libraries, blocking JDBC) — bounded thread pool that grows/shrinks, prevents exhausting resources |
| `Schedulers.immediate()` | Run on the current thread (mainly for testing) |

```java
Mono<String> result = Mono.fromCallable(() -> legacyBlockingLibraryCall())
        .subscribeOn(Schedulers.boundedElastic())   // offload the blocking call to a dedicated pool
        .map(this::processResult);                  // this can stay on the event loop

Flux<Integer> heavyComputation = Flux.range(1, 100)
        .publishOn(Schedulers.parallel())           // switch downstream processing to parallel scheduler
        .map(this::cpuIntensiveTransform);
```

> `subscribeOn` affects the *entire chain upstream*; `publishOn` only affects operators *downstream of it*. This distinction trips up most beginners.

### Reactive Database Access — R2DBC

```java
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findByActiveTrue();                       // derived query, fully non-blocking

    @Query("SELECT * FROM users WHERE last_name = :lastName")
    Flux<User> findByLastName(String lastName);           // custom reactive SQL
}

@Service
public class UserService {
    private final UserRepository repo;

    public Mono<User> createUser(User user) {
        return repo.save(user)                            // INSERT, non-blocking
                .doOnSuccess(saved -> log.info("Created user {}", saved.id()));
    }
}
```

For MongoDB, the equivalent is `ReactiveMongoRepository<T, ID>` from `spring-boot-starter-data-mongodb-reactive` — same non-blocking philosophy, document store instead of relational.

---

## 5. Common Pitfalls & Best Practices

### ❌ Avoid Blocking Calls Inside Reactive Chains

```java
// BAD — blocks the event loop thread, defeats the entire purpose of WebFlux
Mono<User> getUser(Long id) {
    Thread.sleep(1000);                          // ❌ never do this
    User user = jdbcTemplate.queryForObject(...); // ❌ blocking JDBC inside reactive flow
    return Mono.just(user);
}

// GOOD — use R2DBC, or if a blocking call is unavoidable, isolate it explicitly
Mono<User> getUser(Long id) {
    return Mono.fromCallable(() -> legacyJdbcCall(id))
            .subscribeOn(Schedulers.boundedElastic());  // ✅ isolated to a dedicated pool
}
```

### ✅ Error Handling & Logging

- Always have an `onErrorResume` (or equivalent) at the end of public-facing chains — an unhandled error becomes a 500 with a generic message.
- Use `doOnError` for logging *before* recovery so you don't silently swallow root causes.
- Prefer specific exception types in `onErrorResume(Class, ...)` over a blanket `Throwable` catch, so you don't accidentally mask bugs as "graceful" fallbacks.

### ✅ Keep Operator Chains Readable

Long chained pipelines get unreadable fast. Break them into named private methods:

```java
// Hard to read:
return repo.findAll().filter(...).map(...).flatMap(...).filter(...).map(...);

// Better:
return repo.findAll()
        .transform(this::filterActive)
        .transform(this::enrichWithOrderData)
        .transform(this::toResponseDto);
```

`.transform()` lets you extract a reusable named segment of the pipeline as its own `Function<Flux<X>, Flux<Y>>` method.

### Other Best Practices

- Never call `.block()` in a WebFlux app (outside of `main()`/startup code or tests) — it turns your non-blocking app blocking and can deadlock the event loop under load.
- Don't subscribe manually in controllers — returning `Mono`/`Flux` is enough; Spring subscribes for you.
- Set explicit `.timeout()` on external calls — an unresponsive downstream service shouldn't hang your whole pipeline indefinitely.
- Use `Mono<Void>` (not `Mono<Boolean>` returning true) for "fire and complete" operations like deletes.

---

## Summary Checklist

- [ ] Understand Publisher/Subscriber/Subscription/backpressure as the Reactive Streams contract.
- [ ] Know `Mono` (0–1) vs `Flux` (0–N) and pick the right one by return-type intuition.
- [ ] Use `map` for sync transforms, `flatMap` when the transform itself is reactive.
- [ ] Always handle errors with `onErrorResume`/`onErrorReturn`, and log with `doOnError` before recovering.
- [ ] Never block inside a reactive chain — offload unavoidable blocking calls to `boundedElastic()`.
- [ ] Use `zip` for "wait for all", `merge` for unordered combination, `concat` for ordered sequencing.
- [ ] Test with `StepVerifier`, not manual `.block()` + assertions.
- [ ] Use R2DBC/Reactive Mongo for fully non-blocking persistence — mixing in blocking JDBC negates WebFlux's benefits.
- [ ] Keep chains readable with `.transform()` and named helper methods.

## Practice Project Suggestion

**Build a Reactive User Management Service** with these features (perfect next step after your JPA/Hibernate work, since it'll let you contrast blocking vs. non-blocking persistence directly):

1. CRUD endpoints (`GET /users`, `GET /users/{id}`, `POST /users`, `PUT /users/{id}`, `DELETE /users/{id}`) using `Mono`/`Flux` + R2DBC + MySQL or Postgres.
2. An endpoint that calls a second mock "notification service" via `WebClient` after user creation (practice `flatMap` + error fallback if the notification service is down).
3. Pagination using `Flux` + `skip()`/`take()`.
4. Validation that returns proper 400 responses using `onErrorResume` for `ConstraintViolationException`.
5. Full `StepVerifier` test suite for the service layer, and `WebTestClient` (WebFlux's equivalent of `MockMvc`) for controller-level tests.
6. Stretch goal: add Server-Sent Events (`Flux<ServerSentEvent<User>>`) so clients can subscribe to live user-creation events — great way to practice `Sinks` (Reactor's hot-stream publisher type, useful for broadcasting events to multiple subscribers).
