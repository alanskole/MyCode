#include <stdio.h>

int main()
{
    int num1, num2;
    printf("Enter num1 and num2\n");
    scanf("%d %d", &num1, &num2);
    
    printf("num1 = %d, num2 = %d\n", num1, num2);
    
    if(num1 == num2)
        printf("num1 is equal to num2\n");
    else if(num1 > num2)
        printf("num1 is greater than num2\n");
    else 
        printf("num1 is smaller than num2\n");
    return 0;
}
