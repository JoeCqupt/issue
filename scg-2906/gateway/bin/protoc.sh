#!/usr/bin/env bash

# FIXME:
protoc_base_dir=/Users/jiangyuan/devtools/protoc-21.12-osx-x86_64/bin
proto_file_base_dir=$(dirname "$PWD")
#echo $proto_file_base_dir

$protoc_base_dir/protoc --proto_path=$proto_file_base_dir/src/main/resources/proto/ \
--descriptor_set_out=$proto_file_base_dir/src/main/resources/desc/descriptor.pb \
$proto_file_base_dir/src/main/resources/proto/go.proto