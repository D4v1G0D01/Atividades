#include <stdio.h>
#include <string.h>

#define MAX_N 500
#define MAX_M 500

char matrix[MAX_N][MAX_M];

void water_flow(int n, int m) {
    int changes;
    do {
        changes = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '.') {
                    if ((i > 0 && matrix[i-1][j] == 'o') ||
                        (j > 0 && i < n-1 && matrix[i][j-1] == 'o' && matrix[i+1][j-1] == '#') ||
                        (j < m-1 && i < n-1 && matrix[i][j+1] == 'o' && matrix[i+1][j+1] == '#')) {
                        matrix[i][j] = 'o';
                        changes++;
                    }
                }
            }
        }
    } while (changes > 0);
}

int main() {
    int n, m;
    scanf("%d %d", &n, &m);

    for (int i = 0; i < n; i++) {
        scanf("%s", matrix[i]);
    }

    water_flow(n, m);

    for (int i = 0; i < n; i++) {
        printf("%s\n", matrix[i]);
    }

    return 0;
}
