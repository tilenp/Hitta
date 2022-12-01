# Hitta

## Architecture
MVVM with CLEAN architecture. 

Modules:
- app: contains feature UI
- core: contains commonn classes and models
- domain: contains usecases
- data: contains repositories and data sources

Main components:
- views: dispatch user events, observe data flow
- view models: handle events and delegate tasks to use cases, expose data flows
- use cases: handle data flows from repositories and delegate mapping into domain objects to mappers
- repositories: synchronize data between data sources, expose data flows
- data sources: handle responses
- mappers: map objects

Object types:
- dto: represents data structure on the server
- domain: represents data on the screen

## Libraries
- Coroutines and Flows
- Hilt
- Retrofit
