#!/usr/bin/env bash

sm --start ASSETS_FRONTEND -r 3.11.0
sm --start API_DEFINITION THIRD_PARTY_APPLICATION API_EXAMPLE_MICROSERVICE API_PLATFORM_MICROSERVICE


./run_local.sh
