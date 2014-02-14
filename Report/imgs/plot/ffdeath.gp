set terminal pdfcairo
set output 'ffdeath.pdf'
round(x) = x - floor(x) < 0.5 ? floor(x) : ceil(x)
f(x) = 1.0/(round(1.45**(8-x)))
g(x) = 1.0/(round(1.6**(6-x)))
h(x) = 1.0/(round(1.3**(12-x)))

i(x) = x < 8 ? f(x) : f(8)
j(x) = x < 6 ? g(x) : g(6)

set xrange [0:12]
set yrange [0:1.1]
set grid

set key right bottom

set xlabel 'Num surrounding fires'
set ylabel 'P(death)'

plot i(x) with lines title 'Cartesian grid', j(x) with lines title 'Hexagonal grid', h(x) with lines title 'Triangle grid'

