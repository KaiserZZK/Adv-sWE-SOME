import { MedicalHistory } from './medical-history'; 
export class Profile {
    profileId: string;
    userId: string;
    age: number;
    sex: string;
    location: string;
    physicalFitness: string;
    languagePreference: string;
    medicalHistory: MedicalHistory[]; 
}