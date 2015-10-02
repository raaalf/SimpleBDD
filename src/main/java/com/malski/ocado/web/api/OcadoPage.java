package com.malski.ocado.web.api;

import com.malski.ocado.web.modules.ShopNavigator;

public interface OcadoPage {
    void waitToLoad();
    ShopNavigator getNavigator();
}
