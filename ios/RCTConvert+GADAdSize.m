#import "RCTConvert+GADAdSize.h"

@implementation RCTConvert (GADAdSize)

+ (GADAdSize)GADAdSize:(id)json
{
    NSString *adSize = [self NSString:json];
    if ([adSize isEqualToString:@"banner"]) {
        return kGADAdSizeBanner;
    } else if ([adSize isEqualToString:@"fullBanner"]) {
        return kGADAdSizeFullBanner;
    } else if ([adSize isEqualToString:@"largeBanner"]) {
        return kGADAdSizeLargeBanner;
    } else if ([adSize isEqualToString:@"fluid"]) {
        return kGADAdSizeFluid;
    } else if ([adSize isEqualToString:@"skyscraper"]) {
        return kGADAdSizeSkyscraper;
    } else if ([adSize isEqualToString:@"leaderboard"]) {
        return kGADAdSizeLeaderboard;
    } else if ([adSize isEqualToString:@"mediumRectangle"]) {
        return kGADAdSizeMediumRectangle;
    } else if ([adSize isEqualToString:@"smartBannerPortrait"]) {
        CGFloat height = ( UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ) ? 90 : 50;
        return GADAdSizeFromCGSize(CGSizeMake([UIScreen mainScreen].bounds.size.width, height));
    } else if ([adSize isEqualToString:@"smartBannerLandscape"]) {
        CGFloat height = ( UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ) ? 90 : 32;
        return GADAdSizeFromCGSize(CGSizeMake([UIScreen mainScreen].bounds.size.width, height));
    } else if ([adSize isEqualToString:@"300x600"]) {
        return GADAdSizeFromCGSize(CGSizeMake(300, 600));
    } else if ([adSize isEqualToString:@"300x250"]) {
             return GADAdSizeFromCGSize(CGSizeMake(300, 250));
         }
    else {
        return kGADAdSizeInvalid;
    }
}

@end
