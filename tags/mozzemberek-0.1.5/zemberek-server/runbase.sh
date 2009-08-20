surum=0.7.1

get_jni_dir() {
	echo native-$(uname | tr '[:upper:]' '[:lower:]')-$(uname -m)
}
