/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.networkevents.greenrobot.app;

import com.github.pwittchen.networkevents.library.bus.BusWrapper;

import de.greenrobot.event.EventBus;

/**
 * Wrapper for Event Bus using GreenRobot Event Bus under the hood
 */
public final class GreenRobotBusWrapper implements BusWrapper {
    private EventBus greenRobotBus;

    public GreenRobotBusWrapper(EventBus greenRobotBus) {
        this.greenRobotBus = greenRobotBus;
    }

    @Override
    public void register(Object object) {
        greenRobotBus.register(object);
    }

    @Override
    public void unregister(Object object) {
        greenRobotBus.unregister(object);
    }

    @Override
    public void post(Object event) {
        greenRobotBus.post(event);
    }
}
