/*
Generic implementation of the peanutlabs extension.
This file should perform any platform-indepedentent functionality
(e.g. error checking) before calling platform-dependent implementations.
*/

/*
 * NOTE: This file was originally written by the extension builder, but will not
 * be overwritten (unless --force is specified) and is intended to be modified.
 */


#include "peanutlabs_internal.h"
s3eResult peanutlabsInit()
{
    return peanutlabsInit_platform();
}

void peanutlabsTerminate()
{
    peanutlabsTerminate_platform();
}

s3eResult openRewardsCenter(const char* user_id)
{
	return openRewardsCenter_platform(user_id);
}
