
export class PatientDTO 
{
    patientId:number;
    username:string;
    password:string;
    fullName:string;
    dateOfBirth:Date;
    contactNumber:string;
    email:string;
    address:string
    constructor(patientId:number,username:string,password:string, fullName:string,dateOfBirth:Date,contactNumber:string,email:string,address:string)
    {
        this.patientId=patientId;
        this.username=username;
        this.password=password;
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.contactNumber=contactNumber;
        this.email=email;
        this.address=address;
    }
    // logAttributes()
    // {
    //     console.log(this.patientId);
    //     console.log(this.username);
    //     console.log(this.password);
    //     console.log(this.fullName);
    //     console.log(this.dateOfBirth);
    //     console.log(this.contactNumber);
    //     console.log(this.email);
    //     console.log(this.address);
    // }
}

// export interface PatientDTO{
//     patientId:number;
//     username:string;
//     password:string;
//     fullName:string;
//     dateOfBirth:Date;
//     contactNumber:string;
//     email:string;
//     address:string
// }