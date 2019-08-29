LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS+= -L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE := serial_port
LOCAL_SRC_FILES := me_code_yiguitest_SerialPort.c
include $(BUILD_SHARED_LIBRARY)
