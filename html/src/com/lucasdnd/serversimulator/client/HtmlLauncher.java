package com.lucasdnd.serversimulator.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.lucasdnd.serversimulator.ServerSimulator;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 640);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new ServerSimulator();
        }
}