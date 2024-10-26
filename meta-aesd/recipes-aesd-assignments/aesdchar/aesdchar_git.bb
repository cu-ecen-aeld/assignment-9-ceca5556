# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-ceca5556.git;protocol=ssh;branch=assignment-8 \
           file://aesdchar-start-stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "06cf414f180dae80fb71c2ae506ab5a592fec473"

S = "${WORKDIR}/git"

FILES:${PN} += "${bindir}/"
FILES:${PN} += "${sysconfdir}/"
#FILES:${PN} += "${base_libdir}/"

#MODULES_INSTALL_TARGET = "install"
#EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"

#FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop.sh"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop.sh"

do_install:append () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	install -d ${D}${bindir}/
	#install -m 0755 ${S}/misc-modules/* ${D}${bindir}/
	install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${bindir}/
	install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${bindir}/

	# yocto uname -r is differne t than .bb file uname -r
	#install -d ${D}${base_libdir}/modules/aesd-mods/
	#install -m 0755 ${S}/misc-modules/*.ko ${D}${base_libdir}/modules/aesd-mods/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesdchar-start-stop.sh ${D}${sysconfdir}/init.d

}