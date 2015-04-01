/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author ruslan
 */
class SubscriptionChecker {
    private final URL url;

    public SubscriptionChecker(String host) throws MalformedURLException {
        url = new URL("http://" + host + "/Jobber/Subscription");
    }

    @Override
    public String toString() {
        return "[status]   " + url;
    }
    
}
