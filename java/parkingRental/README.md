The program parkingRental is for renting parkingspots from other users; a users can rent out their spots or rent spots from others. It's a lot of code, used SOLID principles and a bunch of tests, unit and integration testing.

There are three different user types:

- user: a normal user with no elevated rights. Can only perform actions on their own acccount. Can create a parkinglot with parkingspots to rent out to other users. A user can also just rent from other users without renting out anything

- manager: an employee at the service that has elevated system rights. They work in customer service and can perform actions on behalf of normal users

- administrator: full rights
