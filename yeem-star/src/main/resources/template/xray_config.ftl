{
    "log": {
        "access": "${xray_access_log}",
        "error": "${xray_error_log}",
        "loglevel": "warning"
    },
    "routing": {
        "rules": [
            {
                "inboundTag": [
                    "api"
                ],
                "outboundTag": "api",
                "type": "field"
            },
            {
                "ip": [
                    "geoip:private"
                ],
                "outboundTag": "blocked",
                "type": "field"
            },
            {
                "outboundTag": "blocked",
                "protocol": [
                    "bittorrent"
                ],
                "type": "field"
            }
        ]
    },
    "dns": null,
    "inbounds": [
        {
            "listen": "127.0.0.1",
            "port": 10085,
            "protocol": "dokodemo-door",
            "settings": {
                "address": "127.0.0.1"
            },
            "streamSettings": null,
            "tag": "api",
            "sniffing": null
        },
        {
            "listen": null,
            "port": 443,
            "protocol": "vless",
            "settings": {
                "clients": ${clients},
                "decryption": "none",
                "fallbacks": []
            },
            "streamSettings": {
                "network": "ws",
                "security": "tls",
                "tlsSettings": {
                    "serverName": "${xray_domain}",
                    "certificates": [
                        {
                            "certificateFile": "${xray_certificate_file}",
                            "keyFile": "${xray_key_file}"
                        }
                    ]
                },
                "wsSettings": {
                    "path": "${xray_ws_path}",
                    "headers": {}
                }
            },
            "tag": "inbound-443",
            "sniffing": {
                "enabled": true,
                "destOverride": [
                    "http",
                    "tls"
                ]
            }
        }
    ],
    "outbounds": [
        {
            "protocol": "freedom",
            "settings": {}
        },
        {
            "protocol": "blackhole",
            "settings": {},
            "tag": "blocked"
        }
    ],
    "transport": null,
    "policy": {
        "system": {
            "statsInboundUplink": false,
            "statsInboundDownlink": false,
            "statsOutboundUplink": false,
            "statsOutboundDownlink": false
        },
        "levels": {
            "1": {
                "statsUserUplink": true,
                "statsUserDownlink": true
            }
        }
    },
    "api": {
        "services": [
            "HandlerService",
            "LoggerService",
            "StatsService"
        ],
        "tag": "api"
    },
    "stats": {},
    "reverse": null,
    "fakeDns": null
}