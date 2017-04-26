#import "TangoPlugin.h"
#import <Tango/Tango.h>

@implementation TangoPlugin

- (void)trigger:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString* phrase = [command.arguments objectAtIndex:0];
        [Tango triggerWithKey:phrase];
    }];
}

- (void)unregisterTango:(CDVInvokedUrlCommand*)command {
    [[UIApplication sharedApplication] unregisterForRemoteNotifications];
}

- (void)registerTango:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString* apiKey = [command.arguments objectAtIndex:0];
        [Tango initializeWithTango:apiKey];
    }];
}

- (void)addSegment:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSMutableArray<NSString *> *segments = [[NSMutableArray alloc] initWithCapacity: command.arguments.count];
        for (NSString *segment in command.arguments) {
            [segments addObject:segment];
        }
        [Tango registerSegmentsWithSegments:segments];
    }];
}

@end
