UPDATE car_film_price
set
price_value1 = ROUND(price0*0.11194 / 100) * 100,
price_value2 = ROUND(price0*0.11194 / 100) * 100,
price_value3 = ROUND(price0*0.07462 / 100) * 100,
price_value4 = ROUND(price0*0.11194 / 100) * 100,
price_value5 = ROUND(price0*0.11194 / 100) * 100,
price_value6 = ROUND(price0*0.2238806 / 100) * 100,
price_value7 = ROUND(price0*0.03731343 / 100) * 100,
price_value8 = ROUND(price0*0.11194 / 100) * 100,
price_value9 = ROUND(price0*0.06716418 / 100) * 100,
price_value10 = ROUND(price0*0.03731343 / 100) * 100
WHERE product_no in ('XPXL03','TK0004')
