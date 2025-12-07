import { createServiceFactory } from '../utils/serviceFactory';
import JavaShareService from '../services/JavaShareService';

export const ShareService = createServiceFactory(JavaShareService);
export default ShareService;
