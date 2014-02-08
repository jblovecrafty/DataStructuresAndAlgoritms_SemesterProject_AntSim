/**
 * 
 */

/**
 * @author joejones
 *
 */
/*
     * Class to hold a pairing of a ColonyNodeView and a MapSpace
     * This is a bit ugly but I wanted to pair the model and the view map classes
     * so I didnt have to loop thru my model of map spaces and then the view of map spaces
     * if there is a better way then please let me know
     */
    class MapViewAndModelPair
    {
        MapSpace mapSpace;
        ColonyNodeView colonyNodeView;
        
        //set up constructor here
        //
        protected MapViewAndModelPair(MapSpace passedMapSpace, ColonyNodeView passedColonyNodeView)
        {
            this.setMapSpace(passedMapSpace);
            this.setColonyNodeView(passedColonyNodeView);
        }

        /**
         * @return the mapSpace
         */
        public MapSpace getMapSpace()
        {
            return mapSpace;
        }

        /**
         * @param mapSpace the mapSpace to set
         */
        public void setMapSpace(MapSpace mapSpace)
        {
            this.mapSpace = mapSpace;
        }

        /**
         * @return the colonyNodeView
         */
        public ColonyNodeView getColonyNodeView()
        {
            return colonyNodeView;
        }

        /**
         * @param colonyNodeView the colonyNodeView to set
         */
        public void setColonyNodeView(ColonyNodeView colonyNodeView)
        {
            this.colonyNodeView = colonyNodeView;
        }
    }