# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

inherit module

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-ceca5556.git;protocol=ssh;branch=master \
           file://0001-makefile-only-builds-scull-and-misc-modules.patch \
           file://scull-start-stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a2b5a4a5d45a30d047a77d0523037be4ad56f4ca"

S = "${WORKDIR}/git"

#FILES:${PN} += "${bindir}/scull_load"
#FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${bindir}/"
FILES:${PN} += "${sysconfdir}/"

LDDINC = "${S}/include"
EXTRA_CFLAGS += "-I${LDDINC}"
TARGET_CFLAGS += "${EXTRA_CFLAGS}"

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}/scull"


inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop.sh"

#do_configure () {
#	:
#}

#do_compile () {
#	oe_runmake
#}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	install -d ${D}${bindir}/
	install -m 0755 ${S}/scull/* ${D}${bindir}/
	#install -m 0755 ${S}/scull/scull_load ${D}${bindir}/scull
	#install -m 0755 ${S}/scull/scull_unload ${D}${bindir}/
	#install -m 0755 ${WORKDIR}/scull-start-stop.sh ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scull-start-stop.sh ${D}${sysconfdir}/init.d

}