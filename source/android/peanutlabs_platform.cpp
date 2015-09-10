/*
 * android-specific implementation of the peanutlabs extension.
 * Add any platform-specific functionality here.
 */
/*
 * NOTE: This file was originally written by the extension builder, but will not
 * be overwritten (unless --force is specified) and is intended to be modified.
 */
#include "peanutlabs_internal.h"

#include "s3eEdk.h"
#include "s3eEdk_android.h"
#include <jni.h>
#include "IwDebug.h"

static jobject g_Obj;
static jmethodID g_openRewardsCenter;

s3eResult peanutlabsInit_platform()
{
    // Get the environment from the pointer
    JNIEnv* env = s3eEdkJNIGetEnv();
    jobject obj = NULL;
    jmethodID cons = NULL;

    // Get the extension class
    jclass cls = s3eEdkAndroidFindClass("peanutlabs");
    if (!cls)
        goto fail;

    // Get its constructor
    cons = env->GetMethodID(cls, "<init>", "()V");
    if (!cons)
        goto fail;

    // Construct the java class
    obj = env->NewObject(cls, cons);
    if (!obj)
        goto fail;

    // Get all the extension methods
    g_openRewardsCenter = env->GetMethodID(cls, "openRewardsCenter", "(Ljava/lang/String;)I");
    if (!g_openRewardsCenter)
        goto fail;



    IwTrace(PEANUTLABS, ("PEANUTLABS init success"));
    g_Obj = env->NewGlobalRef(obj);
    env->DeleteLocalRef(obj);
    env->DeleteGlobalRef(cls);

    // Add any platform-specific initialisation code here
    return S3E_RESULT_SUCCESS;

fail:
    jthrowable exc = env->ExceptionOccurred();
    if (exc)
    {
        env->ExceptionDescribe();
        env->ExceptionClear();
        IwTrace(peanutlabs, ("One or more java methods could not be found"));
    }

    env->DeleteLocalRef(obj);
    env->DeleteGlobalRef(cls);
    return S3E_RESULT_ERROR;

}

void peanutlabsTerminate_platform()
{ 
    // Add any platform-specific termination code here
    JNIEnv* env = s3eEdkJNIGetEnv();
    env->DeleteGlobalRef(g_Obj);
    g_Obj = NULL;
}

s3eResult openRewardsCenter_platform(const char* user_id)
{
    JNIEnv* env = s3eEdkJNIGetEnv();
    jstring user_id_jstr = env->NewStringUTF(user_id);
	IwTrace(PEANUTLABS, ("call method without failing"));
    return (s3eResult)env->CallIntMethod(g_Obj, g_openRewardsCenter, user_id_jstr);
}
