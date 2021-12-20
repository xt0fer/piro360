import dayjs from 'dayjs';
import { IPirovideoPiroV } from 'app/shared/model/pirovideo-piro-v.model';

export interface ITagPiroV {
  id?: number;
  contents?: string | null;
  location?: string | null;
  commentDate?: string | null;
  pirovideo?: IPirovideoPiroV | null;
}

export const defaultValue: Readonly<ITagPiroV> = {};
