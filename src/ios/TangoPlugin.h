#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface TangoPlugin : CDVPlugin

- (void)initializeTango:(CDVInvokedUrlCommand*)command;
- (void)addSegments:(CDVInvokedUrlCommand *)command;
- (void)trigger:(CDVInvokedUrlCommand *)command;

@end
