package com.example.locusmapsandroid;

import com.locuslabs.sdk.llprotected.LLVenueFiles;

public class MyConstants {
    public static final String logTag = "MyApplication";

    public static final String ACCOUNT_ID_LOCUSLABS_DEMO = "A1DGMEMRRWEMBP";

    public static final String VENUE_LAX_ID = "lax";
    public static final String VENUE_LAX_ASSET_VERSION = "2020-10-08T22:26:29";
    public static final LLVenueFiles VENUE_LAX_VENUE_FILES = new LLVenueFiles(
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/${geoJsonId}.json",
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/nav-lax.json",
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/pois-3.0-lax.json",
            "https://content.locuslabs.com/airport/spritesheet-mapbox/lax/A1DGMEMRRWEMBP/spritesheet",
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/style.json",
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/theme.json",
            "https://a.locuslabs.com/accounts/A1DGMEMRRWEMBP/lax/2020-10-08T22:26:29/v5/venueData-lax.json"
    );
}
