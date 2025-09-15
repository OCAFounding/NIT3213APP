package com.example.nit3213app.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213app.databinding.ItemEntityBinding
import com.example.nit3213app.domain.model.EntityModel

/**
 * RecyclerView adapter for displaying entity list
 * 
 * This adapter manages the display of entities in the dashboard RecyclerView.
 * It shows a summary of each entity (property1 and property2) and handles
 * click events to navigate to the details screen.
 * 
 * Key Concepts:
 * - ListAdapter for efficient list updates
 * - DiffUtil for automatic list diffing
 * - ViewBinding for type-safe view access
 * - Click handling for navigation
 * 
 * Assignment Requirements:
 * - Displays entity summary in RecyclerView
 * - Handles item clicks for navigation
 * - Efficient list updates with DiffUtil
 * - Shows property1 and property2 (excludes description)
 */
class EntityAdapter(
    private val onClick: (EntityModel) -> Unit
) : ListAdapter<EntityModel, EntityAdapter.EntityViewHolder>(EntityDiffCallback) {

    // =============================================================================
    // DIFFUTIL CALLBACK
    // =============================================================================
    
    /**
     * DiffUtil callback for efficient list updates
     * 
     * This callback determines how to compare and update list items
     * to minimize unnecessary view updates and improve performance.
     */
    object EntityDiffCallback : DiffUtil.ItemCallback<EntityModel>() {
        
        /**
         * Check if items represent the same entity
         * 
         * @param oldItem The old entity
         * @param newItem The new entity
         * @return true if items represent the same entity
         */
        override fun areItemsTheSame(oldItem: EntityModel, newItem: EntityModel): Boolean {
            return oldItem.property1 == newItem.property1 && 
                   oldItem.property2 == newItem.property2
        }
        
        /**
         * Check if item contents are the same
         * 
         * @param oldItem The old entity
         * @param newItem The new entity
         * @return true if item contents are identical
         */
        override fun areContentsTheSame(oldItem: EntityModel, newItem: EntityModel): Boolean {
            return oldItem == newItem
        }
    }

    // =============================================================================
    // VIEWHOLDER
    // =============================================================================
    
    /**
     * ViewHolder for entity items
     * 
     * This class holds references to the views in each list item
     * and handles binding data to those views.
     */
    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        /**
         * Bind entity data to views
         * 
         * @param entity The entity data to display
         */
        fun bind(entity: EntityModel) {
            // Set entity data to views
            binding.tvProp1.text = entity.property1
            binding.tvProp2.text = entity.property2
            
            // Set click listener for navigation
            binding.root.setOnClickListener {
                onClick(entity)
            }
        }
    }

    // =============================================================================
    // ADAPTER METHODS
    // =============================================================================
    
    /**
     * Create new ViewHolder instance
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new ViewHolder that holds a View of the given view type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEntityBinding.inflate(inflater, parent, false)
        return EntityViewHolder(binding)
    }
    
    /**
     * Bind data to ViewHolder
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the list
     */
    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
