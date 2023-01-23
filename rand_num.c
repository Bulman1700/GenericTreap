#include<stdio.h>
#include<stdlib.h>
#include <time.h>

int main(void)
{
  FILE *opf;
  int n = 10;
  int i;

  srand(time(NULL));

  if ((opf = fopen("num.txt", "w")) == NULL)
    return 1;

  for (i = 0; i < n; i++)
  {
    fprintf(opf, "%d, ", rand() % 200 + 1);
  }

  return 0;
}
