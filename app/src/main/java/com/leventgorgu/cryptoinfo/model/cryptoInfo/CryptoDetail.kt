package com.leventgorgu.cryptoinfo.model.cryptoInfo

import com.google.gson.annotations.SerializedName

data class CryptoDetail(
    val category: String,//***
    val contract_address: List<ContractAddres>,
    val date_added: String,
    val date_launched: Any,
    val description: String,//**
    val id: Int,//***
    val is_hidden: Int,
    val logo: String,//***
    val name: String,//***
    val notice: String,
    val platform: Any,
    val self_reported_circulating_supply: Any,
    val self_reported_market_cap: Any,
    val self_reported_tags: Any,
    val slug: String,
    val subreddit: String,
    val symbol: String,//***
    @SerializedName("tag-groups")
    val tagGroups: List<String>,
    @SerializedName("tag-names")
    val tagNames: List<String>,
    val tags: List<String>,
    val twitter_username: String,
    val urls: Urls//***
)