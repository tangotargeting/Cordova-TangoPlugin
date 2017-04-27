#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface TangoPlugin : CDVPlugin

- (void)registerTango:(CDVInvokedUrlCommand*)command;
- (void)addSegment:(CDVInvokedUrlCommand *)command;
- (void)trigger:(CDVInvokedUrlCommand *)command;
- (void)unregisterTango:(CDVInvokedUrlCommand*)command;

@end
