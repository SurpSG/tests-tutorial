
===============================
1. Elementary test
   - always check whether it works
    2. Test suite
    3. Custom testing framework
    4. JUnit 5
        - Annotations
            * parameterized
            * befeoreEach, afterAll, etc
            * disabled
            * TestInstance
            * Execution
            * timeout
        - extensions
        - Culture in tests
            * naming convention
            * one test - one action. Multiple assertions are allowed.
            * given-when-then/arrange-act-assert
    6. Culture in tests
        - no logic in tests
        - ASAP tests. Inheritance in tests.
        - DRY in tests
2. Assertion lib
    - correct assertion: expected vs actual
    - assertk, assertJ, kotest
    - https://ajaxsystems.atlassian.net/wiki/spaces/Tech/pages/3985572764/Kotlin+assertion+lib+research
3. mocks/stubs/fakes
   - mockito, mockk, powermock

===============================
Integration tests
1. Repository
2. HTTP controller
3. NATS
4. Spring context cache logging
5. Gradle tests logging

===============================
E2E tests
1. ResourceLock
===============================
Bonus
1. Test fixtures
2. Test coverage
3. setup live template
4. Flaky tests
