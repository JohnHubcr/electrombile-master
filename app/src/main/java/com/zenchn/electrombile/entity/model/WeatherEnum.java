package com.zenchn.electrombile.entity.model;

import android.text.TextUtils;

import com.zenchn.electrombile.R;

public enum WeatherEnum {

    晴("晴") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.sunny;
        }

        @Override
        public String getWeatherDesc() {
            return "晴天";
        }

        @Override
        public String getWeather() {
            return "晴天";
        }
    },
    云("云") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.sunny;
        }

        @Override
        public String getWeatherDesc() {
            return "多云";
        }

        @Override
        public String getWeather() {
            return "多云";
        }
    },
    阴("阴") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.cloudy;
        }

        @Override
        public String getWeatherDesc() {
            return "阴天";
        }

        @Override
        public String getWeather() {
            return "阴天";
        }
    },
    雨("雨") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.rain;
        }

        @Override
        public String getWeatherDesc() {
            return "雨";
        }

        @Override
        public String getWeather() {
            return "雨";
        }
    },
    雪("雪") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.snow;
        }

        @Override
        public String getWeatherDesc() {
            return "雪";
        }

        @Override
        public String getWeather() {
            return "雪";
        }
    },
    雾("雾") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.fog;
        }

        @Override
        public String getWeatherDesc() {
            return "雾";
        }

        @Override
        public String getWeather() {
            return "雾";
        }
    },
    沙("沙") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.haze;
        }

        @Override
        public String getWeatherDesc() {
            return "沙";
        }

        @Override
        public String getWeather() {
            return "沙";
        }
    },
    尘("尘") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.haze;
        }

        @Override
        public String getWeatherDesc() {
            return "尘";
        }

        @Override
        public String getWeather() {
            return "尘";
        }
    },
    霾("霾") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.haze;
        }

        @Override
        public String getWeatherDesc() {
            return "霾";
        }

        @Override
        public String getWeather() {
            return "霾";
        }
    },
    未知("未知") {
        @Override
        public int getWeatherDrawable() {
            return R.raw.sunny;
        }

        @Override
        public String getWeatherDesc() {
            return "未知";
        }

        @Override
        public String getWeather() {
            return "未知";
        }
    };

    String weatherType;

    WeatherEnum(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public abstract int getWeatherDrawable();

    public abstract String getWeather();

    public abstract String getWeatherDesc();

    public static WeatherEnum classifyWeather(String weather) {
        if (weather != null && !TextUtils.isEmpty(weather)) {
            String[] weatherStrings = {"晴", "云", "阴", "雨", "雪", "雾", "沙", "尘", "霾"};
            for (String weatherType : weatherStrings) {
                if (weather.contains(weatherType))
                    return WeatherEnum.valueOf(weatherType);
            }
        }
        return WeatherEnum.未知;
    }
}
