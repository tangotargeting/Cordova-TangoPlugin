#import "TangoPlugin.h"
#import <Tango/Tango-Swift.h>
#import <Tango/Tango.h>

@implementation TangoPlugin

- (void)initializeTango:(CDVInvokedUrlCommand *)command {
    NSLog(@"Plugin initialize method called!");
    [self.commandDelegate runInBackground:^{
        NSString* apiKey = [command.arguments objectAtIndex:0];
        NSLog(@"Plugin initialize method called with apiKey: %@.", apiKey);
        [Tango initializeWithTango:apiKey];
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"A mers!"];    
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)addSegments:(CDVInvokedUrlCommand *)command {
    NSLog(@"Plugin addSegments method called!");
    [self.commandDelegate runInBackground:^{
        NSArray<NSString *> *segments = (NSArray<NSString *> *)command.arguments.firstObject;
        
//        NSMutableArray<NSString *> *segments = [[NSMutableArray alloc] initWithCapacity: command.arguments.count];
//        for (NSString *segment in command.arguments) {
//            [segments addObject:segment];
//        }
        [Tango registerSegmentsWithSegments:segments];
    }];
}

- (void)trigger:(CDVInvokedUrlCommand *)command {
    NSLog(@"Plugin trigger method called!");

    [self.commandDelegate runInBackground:^{
        NSString* phrase = [command.arguments objectAtIndex:0];
        NSLog(@"Plugin trigger method called wth %@", phrase);
        [Tango triggerWithKey:phrase];
    }];
}

@end
