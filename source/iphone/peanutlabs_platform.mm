/*
 * iphone-specific implementation of the peanutlabs extension.
 * Add any platform-specific functionality here.
 */
/*
 * NOTE: This file was originally written by the extension builder, but will not
 * be overwritten (unless --force is specified) and is intended to be modified.
 */
#include "peanutlabs_internal.h"
#include "s3eEdk_iphone.h"
#include "s3eDebug.h"
#import "PeanutLabsManager.h"
#import "PlRewardsCenterViewController.h"
#import <UIKit/UIKit.h>

s3eResult peanutlabsInit_platform()
{
    return S3E_RESULT_SUCCESS;
}

void peanutlabsTerminate_platform()
{ 
}

s3eResult openRewardsCenter_platform(const char* user_id)
{
	PeanutLabsManager *manager = [PeanutLabsManager getInstance];
	manager.userId = [[NSString alloc] initWithUTF8String:user_id];
	[manager openRewardsCenter];
    NSLog(@"%@", manager.userId);
	
    return S3E_RESULT_SUCCESS;
}
