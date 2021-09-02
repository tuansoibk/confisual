import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import visualizationReducer from '../features/visualization/visualizationSlice';

export const store = configureStore({
  reducer: {
    counter: counterReducer,
    visualization: visualizationReducer
  },
});
