# Spring multi module project template

[![Coverage Status](https://coveralls.io/repos/github/comstering/spring-multimodule-kotlin-template/badge.svg?branch=develop)](https://coveralls.io/github/comstering/spring-multimodule-kotlin-template?branch=develop)
[![codecov](https://codecov.io/gh/comstering/spring-multimodule-kotlin-template/branch/develop/graph/badge.svg?token=IS7DDLA7F7)](https://codecov.io/gh/comstering/spring-multimodule-kotlin-template)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/comstering/spring-multimodule-kotlin-template/blob/develop/LICENSE)

## Description

This template is spring boot project template.

## Git branch strategy

This template use git flow strategy.

[more about](.github/workflows)

## Project structure

- infrastructure
- domain
- service
- application
- util(Optional)

### Infrastructure

About infra.

- Connect Database
- Request third-party api
- Communication another service
- Cache
- Message queue

### Domain

About entities, and business logic.

- Data entities
- Entity domain logic
- Domain Repository
- Make interface for using service class
  - implement in infrastructure(i.e. use third party data)

### Application

About api.

- RestController
- Graphql
- (+ more UI View)

### Batch

About batch job.

- Batch job
- Use dataflow

### Util(Optional)

About utils.

I don’t make this. Because it is very big module. I think it is project not module.

Maybe I will create util project or use another util project.

- Exception
- (+ utils)
