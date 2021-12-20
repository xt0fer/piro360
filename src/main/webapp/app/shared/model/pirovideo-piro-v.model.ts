import dayjs from 'dayjs';
import { ITagPiroV } from 'app/shared/model/tag-piro-v.model';
import { IPersonPiroV } from 'app/shared/model/person-piro-v.model';

export interface IPirovideoPiroV {
  id?: number;
  title?: string | null;
  notes?: string | null;
  location?: string | null;
  vidurl?: string | null;
  recordDate?: string | null;
  tags?: ITagPiroV[] | null;
  owner?: IPersonPiroV | null;
}

export const defaultValue: Readonly<IPirovideoPiroV> = {};
