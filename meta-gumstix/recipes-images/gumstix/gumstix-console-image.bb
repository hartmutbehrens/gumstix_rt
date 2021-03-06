DESCRIPTION = "The most basic Gumstix image"

inherit image
IMAGE_FEATURES += "package-management"
IMAGE_EXTRA_INSTALL ?= ""

DEPENDS = "virtual/kernel"

AUDIO_INSTALL = " \
  alsa-state \
 "

BASE_INSTALL = " \
  ${MACHINE_EXTRA_RRECOMMENDS} \
  ${@base_contains("DISTRO_FEATURES", "bluetooth", "bluez4", "", d)} \
  avahi-systemd avahi-utils \
  base-files \
  base-passwd \
  bash \
  coreutils \
  dbus \
  devmem2 \
  libpam \ 
  man \	
  man-pages \
  memtester \
  netbase \
  ntp-systemd \
  net-tools \
  rsyslog-systemd \
  sed \
  shadow tinylogin \
  sudo \ 
  systemd systemd-compat-units \
  u-boot-mkimage \
  udev \
  udisks udisks-systemd \
  upower \
  update-alternatives-cworth \
  util-linux \
  which \
  zypper \
 "

FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-rtl8192cu \
  linux-firmware-rtl8192ce \
  linux-firmware-rtl8192su \
  linux-firmware-wl12xx \
 "
NETWORK_INSTALL = " \
  networkmanager \
  networkmanager-tests \
  rfkill \
  wireless-tools \
  ${@base_contains("DISTRO_FEATURES", "wifi", "iw wpa-supplicant", "", d)} \
 "

TOOLS_INSTALL = " \
  binutils \ 
  binutils-symlinks \ 
  bzip2 \
  cpufrequtils \
  dosfstools \
  e2fsprogs \
  evtest \
  findutils \
  make \ 
  gcc \ 
  gcc-symlinks \ 
  gcc-dev \ 
  g++-symlinks \
  gdb \ 
  iputils \
  grep \
  gzip \
  htop \
  nano \
  ldd \ 
  openssh-ssh openssh-keygen openssh-scp openssh-sshd-systemd \
  python-setuptools \
  rt-tests \ 
  strace \ 
  sudo \
  systemd-analyze \
  tar \
  vim \
  wget \
  zip \
 "

IMAGE_INSTALL += " \
  ${BASE_INSTALL} \
  ${FIRMWARE_INSTALL} \
  ${NETWORK_INSTALL} \
  ${ROOTFS_PKGMANAGE} \
  ${TOOLS_INSTALL} \
 "

# this section removes remnants of legacy sysvinit support
# for packages installed above
IMAGE_FILE_BLACKLIST += " \
                        /etc/init.d/NetworkManager \
                        /etc/init.d/avahi-daemon \
                        /etc/init.d/alsa-state \
                        /etc/init.d/dbus-1 \
                        /etc/init.d/dnsmasq \
                        /etc/init.d/networking \
                        /etc/init.d/ntpd \
                        /etc/init.d/sshd \
                        /etc/init.d/udev \
                        /etc/init.d/udev-cache \
                       "

remove_blacklist_files() {
	for i in ${IMAGE_FILE_BLACKLIST}; do
		rm -rf ${IMAGE_ROOTFS}$i
	done

}

ROOTFS_POSTPROCESS_COMMAND =+ "remove_blacklist_files ; "

