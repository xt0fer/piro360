import { IPirovideoPiroV } from 'app/shared/model/pirovideo-piro-v.model';

export interface IPersonPiroV {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  pirovideos?: IPirovideoPiroV[] | null;
}

export const defaultValue: Readonly<IPersonPiroV> = {};
