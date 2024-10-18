#!/bin/bash
# Script to build image for qemu.
# Author: Siddhant Jajoo.

git submodule init
git submodule sync
git submodule update

# local.conf won't exist until this step on first execution
source poky/oe-init-build-env

CONFLINE="MACHINE = \"qemuarm64\""

cat conf/local.conf | grep "${CONFLINE}" > /dev/null
local_conf_info=$?

if [ $local_conf_info -ne 0 ];then
	echo "Append ${CONFLINE} in the local.conf file"
	echo ${CONFLINE} >> conf/local.conf
	
else
	echo "${CONFLINE} already exists in the local.conf file"
fi

# place rm_work into conf file
RMWORK="INHERIT += \"rm_work\""

cat conf/local.conf | grep "${RMWORK}" > /dev/null
local_conf_rm=$?

if [ $local_conf_rm -ne 0 ];then
	echo "Append ${RMWORK} in the local.conf file"
	echo ${RMWORK} >> conf/local.conf
	
else
	echo "${RMWORK} already exists in the local.conf file"
fi

# exclude aesd-assignment from rm work
RM_EXCLUDE="RM_WORK_EXCLUDE += \"aesd-assignments\""

cat conf/local.conf | grep "${RM_EXCLUDE}" > /dev/null
local_conf_exclude=$?

if [ $local_conf_exclude -ne 0 ];then
	echo "Append ${RM_EXCLUDE} in the local.conf file"
	echo ${RM_EXCLUDE} >> conf/local.conf
	
else
	echo "${RM_EXCLUDE} already exists in the local.conf file"
fi

# # exclude aesd-assignment from rm work
# RM_EXCLUDE="RM_WORK_EXCLUDE += \"scull\""

# cat conf/local.conf | grep "${RM_EXCLUDE}" > /dev/null
# local_conf_exclude=$?

# if [ $local_conf_exclude -ne 0 ];then
# 	echo "Append ${RM_EXCLUDE} in the local.conf file"
# 	echo ${RM_EXCLUDE} >> conf/local.conf
	
# else
# 	echo "${RM_EXCLUDE} already exists in the local.conf file"
# fi


bitbake-layers show-layers | grep "meta-aesd" > /dev/null
layer_info=$?

if [ $layer_info -ne 0 ];then
	echo "Adding meta-aesd layer"
	bitbake-layers add-layer ../meta-aesd
else
	echo "meta-aesd layer already exists"
fi

set -e
bitbake core-image-aesd
