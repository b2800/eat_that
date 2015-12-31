# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20151005132833) do

  create_table "ingredients", force: true do |t|
    t.text     "name"
    t.integer  "amount_in_stock"
    t.text     "unit"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "ingredients_recipes", id: false, force: true do |t|
    t.integer  "ingredient_id", null: false
    t.integer  "recipe_id",     null: false
    t.integer  "quantity"
    t.text     "unit"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "ingredients_recipes", ["ingredient_id"], name: "index_ingredients_recipes_on_ingredient_id"
  add_index "ingredients_recipes", ["recipe_id"], name: "index_ingredients_recipes_on_recipe_id"

  create_table "recipes", force: true do |t|
    t.text     "name"
    t.text     "description"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
