#include <com_android_myapplication_nativeutils_JniSurface.h>

static jbyte* g_buffer;
#define TAG "JniSurface" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

/*
 * Class:     com_android_myapplication_nativeutils_JniSurface
 * Method:    init
 * Signature: (Lcom/android/myapplication/SurfaceUtils;)Z
 */
JNIEXPORT void JNICALL Java_com_android_myapplication_nativeutils_JniSurface_init
        (JNIEnv *env, jclass thiz, jobject javaClass){
    LOGI("Java_com_android_myapplication_nativeutils_JniSurface_init:");
    jclass clazz = (*env)->FindClass(env, "com/android/myapplication/SurfaceUtils");
    jmethodID javaSurfaceInit = (*env)->GetMethodID(env, clazz, "surfaceInit", "()V");
    jobject buf = (*env)->CallObjectMethod(env, javaClass, javaSurfaceInit);
    g_buffer = (jbyte*)(*env)->GetDirectBufferAddress(env, buf);
}

/*
 * Class:     com_android_myapplication_nativeutils_JniSurface
 * Method:    release
 * Signature: (Lcom/android/myapplication/SurfaceUtils;)V
 */
JNIEXPORT void JNICALL Java_com_android_myapplication_nativeutils_JniSurface_release
        (JNIEnv *env, jclass thiz, jobject javaClass){
    jclass clazz = (*env)->FindClass(env, "com/android/myapplication/SurfaceUtils");
    jmethodID javaSurfaceRelease = (*env)->GetMethodID(env, clazz, "surfaceRelease", "()V");
    (*env)->CallVoidMethod(env, javaClass, javaSurfaceRelease);
    g_buffer = NULL;
}

/*
 * Class:     com_android_myapplication_nativeutils_JniSurface
 * Method:    render
 * Signature: (Lcom/android/myapplication/SurfaceUtils;[BII)V
 */
JNIEXPORT void JNICALL Java_com_android_myapplication_nativeutils_JniSurface_render
        (JNIEnv *env, jclass thiz, jobject javaClass, uint8_t* pixels, jint w, jint h){
    if (g_buffer != NULL) {
        LOGI("Java_com_android_myapplication_nativeutils_JniSurface_render:"+ sizeof(g_buffer));
        jclass clazz = (*env)->FindClass(env, "com/android/myapplication/SurfaceUtils");
        jmethodID javaSurfaceRender = (*env)->GetMethodID(env, clazz, "surfaceRender", "()V");
        memcpy(g_buffer, pixels, w * h * 2); // RGB565 pixels
        (*env)->CallVoidMethod(env, javaClass, javaSurfaceRender);
    }
}
