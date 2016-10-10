LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jniSurface
LOCAL_SRC_FILES := JniSurface.c
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)