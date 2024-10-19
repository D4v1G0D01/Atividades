#include <stdio.h>
#include <string.h>

#define MAX_N 500
#define MAX_M 500

char matrix[MAX_N][MAX_M];

void spread_water(int i, int j, int n, int m) {
    if (i < 0 || i >= n || j < 0 || j >= m || matrix[i][j] != '.') return;

    matrix[i][j] = 'o'; // Fill with water

    // Recursively spread in all four directions
    spread_water(i + 1, j, n, m);
    spread_water(i - 1, j, n, m);
    spread_water(i, j + 1, n, m);
    spread_water(i, j - 1, n, m);
}

void water_flow(int n, int m) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (matrix[i][j] == 'o') {
                spread_water(i + 1, j, n, m);  // Start spreading from water sources
            }
        }
    }
}

int main() {
    int n, m;

    if(scanf("%d %d", &n, &m) != 2 || n <= 0 || n > MAX_N || m <= 0 || m > MAX_M) {
        fprintf(stderr, "Invalid input dimensions.\n");
        return 1;
    }

    for (int i = 0; i < n; i++) {
        if (scanf("%s", matrix[i]) != 1) {
            fprintf(stderr, "Error reading matrix.\n");
            return 1;
        }
    }

    water_flow(n, m);

    for (int i = 0; i < n; i++) {
        printf("%s\n", matrix[i]);
    }

    return 0;
}
