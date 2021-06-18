#!/bin/bash
mvn clean
mvn package
nohup mvn spring-boot:run > log.out 2>&1 &