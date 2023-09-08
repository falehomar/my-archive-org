package my.archive.org;

import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

@Singleton
public class PageProductionDownloader {

    @EventListener
    public void download(ApplicationStartupEvent event){


    }
}
