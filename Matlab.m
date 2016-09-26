clear, clc, close all;

Nul1     = postpad(vec(dlmread("openingdoor.txt")), 3800);
Nul1(1) = [];
Touch1   = dlmread("touchingknockingmovingmirorothersidewcomp.txt");

CuttButter1 = 7;
CuttButter2 = 20;

[b, a] = butter(6, CuttButter1/(400/2), "high");
Filter1 = filter(b,a , Nul1);
[c, d] = butter(6, CuttButter2/(400/2), "high");
Filter2 = filter(c,d , Nul1)

N1 = 256
P1 = abs(fft(Filter1, N1 ))
P1 = fftshift(P1)
F1 = [-N1/2 : N1/2-1]/N1

N2 = 256
P2 = abs(fft(Filter2, N1 ))
P2 = fftshift(P2)
F2 = [-N1/2 : N1/2-1]/N1

subplot(1,2,1)
 plot(F1, P1), title(sprintf("FFT N=%d, Butter: N=6 C=%d ", N1, CuttButter1)), axis([0 0.5]);

subplot(1,2,2);
 plot(F2, P2), title(sprintf("FFT N=%d, Butter: N=6 C=%d ", N1, CuttButter2)), axis([0 0.5]);

%subplot(1,3,3);
% plot(F1, P1), title(sprintf("Focused on peaks: FFT N = %d", N1)), axis([0.13 0.32 15 15.4]);
