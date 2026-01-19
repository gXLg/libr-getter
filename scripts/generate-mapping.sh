#!/bin/env bash

git submodule update --remote MultiVersion
node MultiVersion
node MultiVersion/generate-mapping.js
