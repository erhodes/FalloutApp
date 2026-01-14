# Active Context: FalloutApp

## Current Work Focus
Desktop target now runs an embedded Ktor server (Netty) alongside the Compose Desktop app.

## Recent Changes
- Added Ktor server dependencies (core, netty, content-negotiation, serialization-json) to desktopMain
- Created `server/Server.kt` with a Ktor module and `/health` endpoint
- Desktop `main.kt` now starts the embedded server on launch and stops it via shutdown hook
- Verified desktop build and run with server starting successfully (no SLF4J provider, falls back to NOP)

## Next Steps
1. Add API endpoints for character CRUD (backed by existing repositories/data sources)
2. Add error handling and logging (consider slf4j simple/logback dependency for desktop)
3. Consider configuration (port, host) via env/args/config file
4. Add tests for server routes (ktor-server-tests) if desired

## Active Decisions and Considerations
- Server is embedded Netty, started with the desktop app; currently headful UI still launches
- Shutdown hook calls `engine.stop(1000, 2000)` for graceful stop
- Health endpoint responds `{ "status": "ok" }` at `/health`

## Important Patterns and Preferences
- Server wiring isolated in `server/Server.kt`; desktop main imports `startEmbeddedServer()`
- Keep port/host defaults (`0.0.0.0:8080`) for now; configurable later

## Learnings and Project Insights
- Ktor 3.0.0 requires matching server artifacts and version catalog entry
- Embedded server start returns `EmbeddedServer<NettyApplicationEngine, ...>`; stop with timeouts
- SLF4J warnings appear without provider; harmless but noisy
