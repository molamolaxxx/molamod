#!/usr/bin/env bash
./gradlew build
cp build/libs/molamod-1.12.2-1.0.jar /home/mola/.minecraft/mods
scp build/libs/molamod-1.12.2-1.0.jar molaspace.xyz:/home/mola/minecraft/forge/mods