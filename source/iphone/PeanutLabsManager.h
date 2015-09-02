//
//  PeanutLabsManager.h
//
//  Created by Peanut Labs Inc on 1/10/15.
//  Copyright (c) 2015 PeanutLabs. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PlRewardsCenterViewController.h"
#import "PlUtils.h"

@class PeanutLabsManager;

@protocol PeanutLabsManagerDelegate

- (void)peanutLabsManager:(PeanutLabsManager *)peanutlabsManager rewardsCenterDidOpen:(NSString *)userId;
- (void)peanutLabsManager:(PeanutLabsManager *)peanutlabsManager rewardsCenterDidClose:(NSString *)userId;

@end

@interface PeanutLabsManager : NSObject <PlRewardsCenterViewControllerDelegate>

@property int appId;
@property (nonatomic, retain) NSString *appKey;
@property (nonatomic, retain) NSString *endUserId;
@property (nonatomic, retain) NSString *userId;
@property (nonatomic, retain) NSString *dob;
@property (nonatomic, retain) NSString *sex;

+ (PeanutLabsManager*)getInstance;

- (void)openRewardsCenter;
- (NSString *)generateUserId;
- (void)addCustomParameter: (NSString *)key value:(NSString *) value;
- (NSMutableDictionary *)getCustomVars;

@property (strong, nonatomic) id<PeanutLabsManagerDelegate> delegate;

@end
