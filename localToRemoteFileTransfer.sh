#!/bin/bash

localDirectory=./
remoteDirectory=/root/workspace
remoteUser=root
host=119.15.159.104

rsync --progress -ravz -e "ssh -i ./sshKeys/$host.ppk -p9876" \
--exclude 'sshKeys' \
--exclude '.git' \
--exclude 'localToRemoteFileTransfer.sh' \
--exclude 'loginToRemoteServer.sh' \
--exclude 'README' \
$localDirectory \
$remoteUser@$host:$remoteDirectory \
--delete